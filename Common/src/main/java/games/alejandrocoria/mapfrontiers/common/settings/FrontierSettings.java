package games.alejandrocoria.mapfrontiers.common.settings;

import games.alejandrocoria.mapfrontiers.MapFrontiers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class FrontierSettings {
    public enum Action {
        CreateGlobalFrontier, DeleteGlobalFrontier, UpdateGlobalFrontier, UpdateSettings, SharePersonalFrontier;

        public final static Action[] valuesArray = values();
    }

    public enum ActionV3 {
        CreateFrontier(Action.CreateGlobalFrontier),
        DeleteFrontier(Action.DeleteGlobalFrontier),
        UpdateFrontier(Action.UpdateGlobalFrontier),
        UpdateSettings(Action.UpdateSettings),
        PersonalFrontier(Action.SharePersonalFrontier);

        private final Action action;
        ActionV3(Action action) {
            this.action = action;
        }

        public Action toAction() {
            return action;
        }
    }

    private final SettingsGroup OPs;
    private final SettingsGroup owners;
    private final SettingsGroup everyone;
    private List<SettingsGroup> customGroups;
    private int changeCounter = 1;

    public FrontierSettings() {
        OPs = new SettingsGroup("OPs", true);
        owners = new SettingsGroup("Owner", true);
        everyone = new SettingsGroup("Everyone", true);
        customGroups = new ArrayList<>();
    }

    public void resetToDefault() {
        OPs.addAction(Action.CreateGlobalFrontier);
        OPs.addAction(Action.DeleteGlobalFrontier);
        OPs.addAction(Action.UpdateGlobalFrontier);
        OPs.addAction(Action.UpdateSettings);

        owners.addAction(Action.DeleteGlobalFrontier);
        owners.addAction(Action.UpdateGlobalFrontier);

        everyone.addAction(Action.SharePersonalFrontier);
    }

    public SettingsGroup getOPsGroup() {
        return OPs;
    }

    public SettingsGroup getOwnersGroup() {
        return owners;
    }

    public SettingsGroup getEveryoneGroup() {
        return everyone;
    }

    public List<SettingsGroup> getCustomGroups() {
        return customGroups;
    }

    public SettingsGroup createCustomGroup(String name) {
        SettingsGroup group = new SettingsGroup(name, false);
        customGroups.add(group);
        return group;
    }

    public void removeCustomGroup(SettingsGroup group) {
        customGroups.remove(group);
    }

    public boolean checkAction(Action action, @Nullable SettingsUser player, boolean isOP, @Nullable SettingsUser owner) {
        if (player == null) {
            return false;
        }

        if (isOP && OPs.hasAction(action)) {
            return true;
        }

        if (player.equals(owner) && owners.hasAction(action)) {
            return true;
        }

        if (everyone.hasAction(action)) {
            return true;
        }

        for (SettingsGroup group : customGroups) {
            if (group.hasAction(action) && group.hasUser(player)) {
                return true;
            }
        }

        return false;
    }

    public SettingsProfile getProfile(ServerPlayer player) {
        SettingsProfile profile = new SettingsProfile();
        SettingsUser user = new SettingsUser(player);

        for (Action action : owners.getActions()) {
            profile.setAction(action, SettingsProfile.State.Owner);
        }

        if (MapFrontiers.isOPorHost(player)) {
            for (Action action : OPs.getActions()) {
                profile.setAction(action, SettingsProfile.State.Enabled);
            }
        }

        for (Action action : everyone.getActions()) {
            profile.setAction(action, SettingsProfile.State.Enabled);
        }

        if (profile.isAllEnabled()) {
            return profile;
        }

        for (SettingsGroup group : customGroups) {
            if (group.hasUser(user)) {
                for (Action action : group.getActions()) {
                    profile.setAction(action, SettingsProfile.State.Enabled);
                }
            }
        }

        return profile;
    }

    public boolean readFromNBT(CompoundTag nbt) {
        boolean needBackup = false;
        try {
            int version = nbt.getInt("Version");
            if (version == 0) {
                MapFrontiers.LOGGER.warn("Data version in settings not found, expected " + MapFrontiers.SETTINGS_DATA_VERSION);
                needBackup = true;
            } else if (version < 3) {
                MapFrontiers.LOGGER.warn("Data version in settings lower than expected. The mod uses " + MapFrontiers.SETTINGS_DATA_VERSION);
                needBackup = true;
            } else if (version > MapFrontiers.SETTINGS_DATA_VERSION) {
                MapFrontiers.LOGGER.warn("Data version in settings higher than expected. The mod uses " + MapFrontiers.SETTINGS_DATA_VERSION);
                needBackup = true;
            }

            CompoundTag OPsTag = nbt.getCompound("OPs");
            OPs.readFromNBT(OPsTag, version);

            CompoundTag ownersTag = nbt.getCompound("Owners");
            owners.readFromNBT(ownersTag, version);

            CompoundTag everyoneTag = nbt.getCompound("Everyone");
            everyone.readFromNBT(everyoneTag, version);

            customGroups.clear();
            ListTag customGroupsTagList = nbt.getList("customGroups", Tag.TAG_COMPOUND);
            for (int i = 0; i < customGroupsTagList.size(); ++i) {
                SettingsGroup group = new SettingsGroup();
                CompoundTag groupTag = customGroupsTagList.getCompound(i);
                group.readFromNBT(groupTag, version);
                customGroups.add(group);
            }

            ensureUpdateSettingsAction();
        } catch (Exception ignored) {
            return true;
        }

        return needBackup;
    }

    public void writeToNBT(CompoundTag nbt) {
        CompoundTag OPsTag = new CompoundTag();
        OPs.writeToNBT(OPsTag);
        nbt.put("OPs", OPsTag);

        CompoundTag ownersTag = new CompoundTag();
        owners.writeToNBT(ownersTag);
        nbt.put("Owners", ownersTag);

        CompoundTag everyoneTag = new CompoundTag();
        everyone.writeToNBT(everyoneTag);
        nbt.put("Everyone", everyoneTag);

        ListTag customGroupsTagList = new ListTag();
        for (SettingsGroup group : customGroups) {
            CompoundTag groupTag = new CompoundTag();
            group.writeToNBT(groupTag);
            customGroupsTagList.add(groupTag);
        }
        nbt.put("customGroups", customGroupsTagList);

        nbt.putInt("Version", MapFrontiers.SETTINGS_DATA_VERSION);
    }

    public void fromBytes(FriendlyByteBuf buf) {
        OPs.fromBytes(buf);
        owners.fromBytes(buf);
        everyone.fromBytes(buf);

        customGroups = new ArrayList<>();
        int groupsCount = buf.readInt();
        for (int i = 0; i < groupsCount; ++i) {
            SettingsGroup group = new SettingsGroup();
            group.fromBytes(buf);
            customGroups.add(group);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        OPs.toBytes(buf);
        owners.toBytes(buf);
        everyone.toBytes(buf);

        buf.writeInt(customGroups.size());
        for (SettingsGroup group : customGroups) {
            group.toBytes(buf);
        }
    }

    public static List<Action> getAvailableActions(String groupName) {
        List<Action> actions = new ArrayList<>();

        if (!groupName.contentEquals("Owner")) {
            actions.add(Action.CreateGlobalFrontier);
            actions.add(Action.UpdateSettings);
            actions.add(Action.SharePersonalFrontier);
        }

        actions.add(Action.DeleteGlobalFrontier);
        actions.add(Action.UpdateGlobalFrontier);

        return actions;
    }

    public static List<ActionV3> getAvailableActionsV3(String groupName) {
        List<ActionV3> actions = new ArrayList<>();

        if (!groupName.contentEquals("Owner")) {
            actions.add(ActionV3.CreateFrontier);
            actions.add(ActionV3.UpdateSettings);
            actions.add(ActionV3.PersonalFrontier);
        }

        actions.add(ActionV3.DeleteFrontier);
        actions.add(ActionV3.UpdateFrontier);

        return actions;
    }

    public void setChangeCounter(int changeCounter) {
        this.changeCounter = changeCounter;
    }

    public int getChangeCounter() {
        return changeCounter;
    }

    public void advanceChangeCounter() {
        ++changeCounter;
    }

    private void ensureUpdateSettingsAction() {
        if (OPs.hasAction(Action.UpdateSettings) || owners.hasAction(Action.UpdateSettings)
                || everyone.hasAction(Action.UpdateSettings)) {
            return;
        }

        for (SettingsGroup group : customGroups) {
            if (group.hasAction(Action.UpdateSettings)) {
                return;
            }
        }

        OPs.addAction(Action.UpdateSettings);
    }
}
