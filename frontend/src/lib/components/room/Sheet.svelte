<script lang="ts">
  import * as Sheet from "$lib/components/ui/sheet/index.js";
  import { ChevronDoubleLeft } from "@steeze-ui/heroicons";
  import * as Tabs from "$lib/components/ui/tabs/index.js";

  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import { Game } from "$lib/Game.svelte";

  let { isModifyOpen, selectedIssue, isAddOpen } = $props();

  import IssuesTab from "$lib/components/room/Tabs/IssuesTab.svelte";
  import ProfileTab from "$lib/components/room/Tabs/ProfileTab.svelte";
</script>

<Sheet.Root>
  <Sheet.Trigger class="absolute top-5 right-5 color-gray-700">
    <ButtonIcon icon={ChevronDoubleLeft} size="size-6" theme="solid"
    ></ButtonIcon>
  </Sheet.Trigger>
  <Sheet.Content side="right" class="bg-[#f4f4f9] text-gray-900">
    <Tabs.Root value="profile-settings" class="w-full">
      <Tabs.List class="w-full flex bg-gray-200 mt-5 justify-between gap-1">
        <Tabs.Trigger
          class="font-semibold bg-white flex-1"
          value="profile-settings">Profile Settings</Tabs.Trigger
        >

        {#if Game.storage.user.role == "Administrator"}
          <Tabs.Trigger class="font-semibold bg-white flex-1" value="issues"
            >Issues</Tabs.Trigger
          >
        {/if}
      </Tabs.List>

      <Tabs.Content value="profile-settings">
        <ProfileTab />
      </Tabs.Content>

      {#if Game.storage.user.role == "Administrator"}
        <Tabs.Content value="issues">
          <IssuesTab {isModifyOpen} {selectedIssue} {isAddOpen} />
        </Tabs.Content>
      {/if}
    </Tabs.Root>
  </Sheet.Content>
</Sheet.Root>
