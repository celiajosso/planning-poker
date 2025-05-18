<script lang="ts">
  import DeleteIssue from "../Actions/Row/Issue/DeleteIssue.svelte";
  import ModifyIssue from "../Actions/Row/Issue/ModifyIssue.svelte";
  import VoteIssue from "../Actions/Row/Issue/VoteIssue.svelte";

  let { isModifyOpen, selectedIssue, issue } = $props();
</script>

<div class="flex flex-row items-center h-full gap-2">
  <DeleteIssue {issue} />
  <ModifyIssue {isModifyOpen} {selectedIssue} {issue} />
  <VoteIssue {issue} />
</div>
