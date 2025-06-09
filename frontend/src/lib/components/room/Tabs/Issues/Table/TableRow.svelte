<script lang="ts">
  import DeleteIssue from "$lib/components/room/Tabs/Issues/Actions/DeleteIssue.svelte";
  import VoteIssue from "$lib/components/room/Tabs/Issues/Actions/VoteIssue.svelte";
  import ModifyIssue from "$lib/components/room/Tabs/Issues/Actions/ModifyIssue.svelte";
  import * as Table from "$lib/components/ui/table/index.js";
  import CompleteUncompleteIssue from "../Actions/CompleteUncompleteIssue.svelte";

  let {
    isModifyOpen = $bindable(),
    isDeleteOpen = $bindable(),
    selectedIssue = $bindable(),
    issue,
  } = $props();
</script>

{#if !issue.isSaved}
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
      <DeleteIssue bind:isDeleteOpen {issue} bind:selectedIssue />
      <ModifyIssue bind:isModifyOpen {issue} bind:selectedIssue />
      <VoteIssue {issue} />
      <CompleteUncompleteIssue {issue} bind:selectedIssue />
    </Table.Cell>
  </Table.Row>
{/if}
