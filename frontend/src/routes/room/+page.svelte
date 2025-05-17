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
  <div class="absolute left-0 bottom-4 flex justify-center items-center w-full">
    <div
      class="bg-white rounded-xl flex px-4 py-2 gap-4 flex-col border-2 border-gray-300"
    >
      <RoomActions />
    </div>
  </div>
{/if}
