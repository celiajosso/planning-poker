<script lang="ts">
  import { onMount } from "svelte";
  import { goto } from "$app/navigation";
  import { Game } from "$lib/Game.svelte";
  import { WebSocketManager } from "$lib/WebsocketManager";

  import Sheet from "../../lib/components/room/Sheet.svelte";
  import RoomActions from "$lib/components/room/RoomActions.svelte";

  let isModifyOpen = $state(false);
  let isAddOpen = $state(false);
  let loaded = $state(false);

  let selectedIssue = null;

  let canvas: HTMLCanvasElement;

  onMount(async () => {
    if (WebSocketManager.socket === undefined) {
      goto("/");
    } else {
      await Game.init(canvas);
      loaded = true;
    }
  });
</script>

<canvas bind:this={canvas} class="h-dvh w-dvw" />
{#if loaded}
  <Sheet {isModifyOpen} {selectedIssue} {isAddOpen} />
  <RoomActions />
{/if}
