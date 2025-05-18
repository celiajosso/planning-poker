<script lang="ts">
  import { Label } from "$lib/components/ui/label";

  import ScoreSelection from "$lib/components/room/Tabs/Issues/ScoreSelection.svelte";
</script>

<div class="grid grid-cols-4 items-center gap-4">
  <Label for="issue-description" class="text-right">Score</Label>
  <ScoreSelection />
</div>
