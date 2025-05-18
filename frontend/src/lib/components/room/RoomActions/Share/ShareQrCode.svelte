<script>
  import { Game } from "../../../../Game.svelte";
  import QrCode from "svelte-qrcode";

  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";
</script>

<DropdownMenu.Item>
  <QrCode value={`${window.location.origin}?roomId=${Game.storage.room.id}`} />
</DropdownMenu.Item>
