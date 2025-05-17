<script lang="ts">
  import RoomShare from "$lib/components/room/RoomActions/RoomShare.svelte";
  import RoomQuit from "$lib/components/room/RoomActions/RoomQuit.svelte";
  import RoomReset from "$lib/components/room/RoomActions/RoomReset.svelte";
</script>

<div class="flex items-center gap-4 justify-center text-gray-700 mt-1">
  <RoomReset />
  <RoomQuit />
  <RoomShare />
</div>
