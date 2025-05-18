<script>
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";

  import ShareQrCode from "./Share/ShareQrCode.svelte";
  import ShareId from "./Share/ShareId.svelte";
  import ShareLink from "./Share/ShareLink.svelte";
  import ShareTrigger from "./Share/ShareTrigger.svelte";
</script>

<DropdownMenu.Root>
  <ShareTrigger />
  <DropdownMenu.Content class="w-40">
    <DropdownMenu.Label>Share Room</DropdownMenu.Label>
    <DropdownMenu.Separator />
    <DropdownMenu.Group>
      <ShareQrCode />
      <ShareId />
      <ShareLink />
    </DropdownMenu.Group>
  </DropdownMenu.Content>
</DropdownMenu.Root>
