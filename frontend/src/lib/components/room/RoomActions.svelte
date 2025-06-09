<script lang="ts">
  import RoomShare from "$lib/components/room/RoomActions/RoomShare.svelte";
  import RoomQuit from "$lib/components/room/RoomActions/RoomQuit.svelte";
  import RoomReset from "$lib/components/room/RoomActions/RoomReset.svelte";
  import RoomNewRound from "./RoomActions/RoomNewRound.svelte";
</script>

<div
  class="absolute bottom-4 left-1/2 -translate-x-1/2 flex justify-center items-center text-gray-700 bg-white rounded-xl px-4 py-2 gap-2 border-2 border-gray-300"
>
  <RoomNewRound />
  <RoomReset />
  <RoomQuit />
  <RoomShare />
</div>
