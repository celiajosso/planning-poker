<script lang="ts">
    import {Label} from "$lib/components/ui/label";

    import ScoreSelection from "./ScoreSelection.svelte";

    let {value = $bindable()} = $props();
</script>

<div class="grid grid-cols-4 items-center gap-4">
    <Label class="text-right" for="issue-description">Score</Label>
    <ScoreSelection {value}/>
</div>
