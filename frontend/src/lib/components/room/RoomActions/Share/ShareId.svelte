<script>
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Square2Stack } from "@steeze-ui/heroicons";
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";

  import { copyRoomIdClipboard } from "../../../../../routes/room/script";
</script>

<DropdownMenu.Item onclick={copyRoomIdClipboard}>
  Share Id
  <DropdownMenu.Shortcut>
    <Icon class="color-gray-800 size-4" src={Square2Stack} theme="outline" />
  </DropdownMenu.Shortcut>
</DropdownMenu.Item>
