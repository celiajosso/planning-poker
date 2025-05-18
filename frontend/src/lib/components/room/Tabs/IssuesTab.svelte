<script lang="ts">
  import { Game } from "$lib/Game.svelte";

  import * as Table from "$lib/components/ui/table/index.js";
  import * as Tabs from "$lib/components/ui/tabs/index.js";
  import ImportButton from "$lib/components/room/Tabs/Issues/ImportButton.svelte";
  import ExportButton from "$lib/components/room/Tabs/Issues/ExportButton.svelte";
  import AddIssue from "$lib/components/room/Tabs/Issues/AddIssue.svelte";
  import TableHeader from "./Issues/Table/TableHeader.svelte";
  import TableRow from "./Issues/Table/TableRow.svelte";

  let { isModifyOpen, selectedIssue, isAddOpen } = $props();
</script>

<Tabs.Content value="issues">
  <div class="grid gap-2 p-5 my-5 border border-gray-300 rounded-lg">
    <div class="flex flex-row justify-between items-center">
      <div>
        <p class="font-bold text-xl">Issues</p>
        <p class="text-sm">Make changes to your User Stories.</p>
      </div>
      <div class="flex flex-row items-center h-full gap-2">
        <ImportButton />
        <ExportButton />
      </div>
    </div>
    {#if Game.storage.room.stories && Game.storage.room.stories.length != 0}
      <Table.Root>
        <TableHeader />
        <Table.Body>
          {#each Game.storage.room.stories as issue}
            <TableRow {isModifyOpen} {selectedIssue} {isAddOpen} {issue} />
          {/each}
        </Table.Body>
      </Table.Root>
    {:else}
      <p class="text-center w-full text-gray-500">No issue</p>
    {/if}
    <div class="flex justify-center w-full">
      <AddIssue {isAddOpen} />
    </div>
  </div>
</Tabs.Content>
