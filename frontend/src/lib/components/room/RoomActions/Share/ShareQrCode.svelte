<script>
    import {Game} from "../../../../Game.svelte";
    import QrCode from "svelte-qrcode";
</script>

<DropdownMenu.Item>
    <QrCode value={`${window.location.origin}?roomId=${Game.storage.room.id}`}/>
</DropdownMenu.Item>
