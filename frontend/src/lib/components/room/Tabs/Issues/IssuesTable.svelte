<script lang="ts">
  import { Game } from "$lib/Game.svelte";

  import * as Table from "$lib/components/ui/table/index.js";
  import TableActions from "$lib/components/room/Tabs/Issues/Table/TableActions.svelte";
  import TableHeader from "$lib/components/room/Tabs/Issues/Table/TableHeader.svelte";

  let { isModifyOpen, selectedIssue } = $props();
</script>

<Table.Root>
  <TableHeader />

  <Table.Body>
    {#each Game.storage.room.stories as issue}
      <Table.Row>
        <Table.Cell>{issue.title}</Table.Cell>
        <Table.Cell>
          <p class="truncate max-w-96">
            {issue.description}
          </p></Table.Cell
        >
        <Table.Cell>
          <!-- <ScoreSelection /> -->
          {issue.finalEstimate}
        </Table.Cell>
        <Table.Cell>
          <TableActions {isModifyOpen} {selectedIssue} {issue} />
        </Table.Cell>
      </Table.Row>
    {/each}
  </Table.Body>
</Table.Root>
