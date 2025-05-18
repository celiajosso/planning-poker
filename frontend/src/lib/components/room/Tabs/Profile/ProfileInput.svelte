<script lang="ts">
  import { Game } from "$lib/Game.svelte";

  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
</script>

<div class="flex gap-4 mt-2 items-center">
  <Label for="name"><p class="whitespace-nowrap">Username</p></Label>
  <Input id="name" bind:value={Game.storage.user.username} type="text" />
</div>
