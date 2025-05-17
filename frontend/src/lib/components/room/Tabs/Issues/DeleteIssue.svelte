<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Trash } from "@steeze-ui/heroicons";

  import * as AlertDialog from "$lib/components/ui/alert-dialog/index.js";
  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  let { issue } = $props();
</script>

<AlertDialog.Root>
  <AlertDialog.Trigger
    ><Tooltip.Root>
      <AlertDialog.Trigger asChild>
        <Tooltip.Root>
          <Tooltip.Trigger>
            <ButtonIcon icon={Trash} size="size-5" theme="outline"></ButtonIcon>
          </Tooltip.Trigger>
          <Tooltip.Content>
            <p>Delete</p>
          </Tooltip.Content>
        </Tooltip.Root>
      </AlertDialog.Trigger>

      <Tooltip.Content>
        <p>Delete</p>
      </Tooltip.Content>
    </Tooltip.Root></AlertDialog.Trigger
  >
  <AlertDialog.Content>
    <AlertDialog.Header>
      <AlertDialog.Title>Are you sure?</AlertDialog.Title>
      <AlertDialog.Description>
        This action cannot be undone. This will permanently delete your issue.
      </AlertDialog.Description>
    </AlertDialog.Header>
    <AlertDialog.Footer>
      <AlertDialog.Cancel>Cancel</AlertDialog.Cancel>
      <AlertDialog.Action onclick={() => Game.deleteStory(issue.id)}
        >Continue</AlertDialog.Action
      >
    </AlertDialog.Footer>
  </AlertDialog.Content>
</AlertDialog.Root>
