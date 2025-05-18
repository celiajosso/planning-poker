<script lang="ts">
  import DeleteIssue from "$lib/components/room/Tabs/Issues/Actions/Row/Issue/DeleteIssue.svelte";
  import VoteIssue from "$lib/components/room/Tabs/Issues/Actions/Row/Issue/VoteIssue.svelte";
  import ModifyIssue from "$lib/components/room/Tabs/Issues/Actions/Row/Issue/ModifyIssue.svelte";

  let { isModifyOpen, selectedIssue, issue } = $props();
</script>

<DeleteIssue {issue} />
<ModifyIssue {isModifyOpen} {selectedIssue} {issue} />
<VoteIssue {issue} />
