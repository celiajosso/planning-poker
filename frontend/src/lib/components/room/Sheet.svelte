<script lang="ts">
  import * as Sheet from "$lib/components/ui/sheet/index.js";
  import { ChevronDoubleLeft } from "@steeze-ui/heroicons";
  import * as Tabs from "$lib/components/ui/tabs/index.js";

  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import { Game } from "$lib/Game.svelte";

  let { isModifyOpen, selectedIssue, isAddOpen } = $props();

  import IssuesTab from "$lib/components/room/Tabs/IssuesTab.svelte";
  import ProfileTab from "$lib/components/room/Tabs/ProfileTab.svelte";
  import TabsList from "./Tabs/TabsList.svelte";
  import SheetTrigger from "./SheetTrigger.svelte";
</script>

<Sheet.Root>
  <SheetTrigger />
  <Sheet.Content side="right" class="bg-[#f4f4f9] text-gray-900">
    <Tabs.Root value="profile-settings" class="w-full">
      <TabsList />
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
