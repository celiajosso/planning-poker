<script>
  import { Game } from "../../../Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Share, Square2Stack } from "@steeze-ui/heroicons";
  import QrCode from "svelte-qrcode";

  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  import {
    copyRoomIdClipboard,
    copyRoomLinkClipboard,
  } from "../../../../routes/room/script";
</script>

<DropdownMenu.Root>
  <DropdownMenu.Trigger>
    <Tooltip.Root>
      <Tooltip.Trigger>
        <ButtonIcon icon={Share} size="size-6" theme="outline"></ButtonIcon>
      </Tooltip.Trigger>
      <Tooltip.Content>
        <p>Share Room</p>
      </Tooltip.Content>
    </Tooltip.Root>
  </DropdownMenu.Trigger>
  <DropdownMenu.Content class="w-40">
    <DropdownMenu.Label>Share Room</DropdownMenu.Label>
    <DropdownMenu.Separator />
    <DropdownMenu.Group>
      <DropdownMenu.Item>
        <QrCode
          value={`${window.location.origin}?roomId=${Game.storage.room.id}`}
        />
      </DropdownMenu.Item>
      <DropdownMenu.Item onclick={copyRoomIdClipboard}>
        Share Id
        <DropdownMenu.Shortcut
          ><Icon
            class="color-gray-800 size-4"
            src={Square2Stack}
            theme="outline"
          /></DropdownMenu.Shortcut
        >
      </DropdownMenu.Item>
      <DropdownMenu.Item onclick={copyRoomLinkClipboard}>
        Share link
        <DropdownMenu.Shortcut
          ><Icon
            class="color-gray-800 size-4"
            src={Square2Stack}
            theme="outline"
          /></DropdownMenu.Shortcut
        >
      </DropdownMenu.Item>
    </DropdownMenu.Group>
  </DropdownMenu.Content>
</DropdownMenu.Root>
