<script lang="ts">
  import DeleteIssue from "$lib/components/room/Tabs/Issues/Actions/DeleteButton.svelte";
  import VoteIssue from "$lib/components/room/Tabs/Issues/Actions/VoteButton.svelte";
  import ModifyIssue from "$lib/components/room/Tabs/Issues/Actions/ModifyButton.svelte";

  let { isModifyOpen, selectedIssue, issue } = $props();
</script>

<DeleteIssue {issue} />
<ModifyIssue {isModifyOpen} {selectedIssue} {issue} />
<VoteIssue {issue} />
