<script lang="ts">
  import { Game } from "$lib/Game.svelte";

  import * as Table from "$lib/components/ui/table/index.js";
  import * as Tabs from "$lib/components/ui/tabs/index.js";

  import ImportButton from "$lib/components/room/Tabs/Issues/ImportButton.svelte";
  import ExportButton from "$lib/components/room/Tabs/Issues/ExportButton.svelte";
  import AddIssue from "$lib/components/room/Tabs/Issues/AddIssue.svelte";
  import DeleteIssue from "$lib/components/room/Tabs/Issues/DeleteIssue.svelte";
  import VoteIssue from "$lib/components/room/Tabs/Issues/VoteIssue.svelte";
  import ModifyIssue from "$lib/components/room/Tabs/Issues/ModifyIssue.svelte";

  let { isModifyOpen, selectedIssue, isAddOpen, issue } = $props();
</script>

<Table.Row>
  <Table.Cell>{issue.title}</Table.Cell>
  <Table.Cell class="truncate max-w-96">
    {issue.description}
  </Table.Cell>
  <Table.Cell>
    <!-- <ScoreSelection /> -->
    {issue.finalEstimate}
  </Table.Cell>
  <Table.Cell class="flex flex-row items-center h-full gap-2">
    <DeleteIssue {issue} />
    <ModifyIssue {isModifyOpen} {selectedIssue} {issue} />
    <VoteIssue {issue} />
  </Table.Cell>
</Table.Row>
