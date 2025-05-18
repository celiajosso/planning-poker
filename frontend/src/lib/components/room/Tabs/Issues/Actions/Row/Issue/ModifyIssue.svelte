<script lang="ts">
  import FormRowScore from "./../Form/FormRowScore.svelte";
  import { Game } from "$lib/Game.svelte";
  import { PencilSquare } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  import ScoreSelection from "$lib/components/room/Tabs/Issues/ScoreSelection.svelte";
  import FormRow from "./../Form/FormRow.svelte";

  let { isModifyOpen, selectedIssue, issue } = $props();
</script>

<Tooltip.Root>
  <Tooltip.Trigger>
    <ButtonIcon
      onclick={() => {
        selectedIssue = issue;
        isModifyOpen = true;
      }}
      icon={PencilSquare}
      size="size-5"
      theme="outline"
    ></ButtonIcon>
  </Tooltip.Trigger>
  <Tooltip.Content>
    <p>Modify</p>
  </Tooltip.Content>
</Tooltip.Root>
<Dialog.Root bind:open={isModifyOpen}>
  <Dialog.Content class="sm:max-w-[425px]">
    <form
      onsubmit={(e) => {
        console.log(e);
        Game.updateStory(selectedIssue!.id, e);
        isModifyOpen = false;
      }}
    >
      <Dialog.Header>
        <Dialog.Title>Edit issue</Dialog.Title>
        <Dialog.Description>
          Make changes to your issue here. Click save when you're done.
        </Dialog.Description>
      </Dialog.Header>
      <div class="grid gap-4 py-4">
        <FormRow field="Title" value={selectedIssue!.title} required />
        <FormRow field="Description" value={selectedIssue!.title} required />
        <FormRowScore />
      </div>
      <Dialog.Footer>
        <Button type="submit">Save changes</Button>
      </Dialog.Footer>
    </form>
  </Dialog.Content>
</Dialog.Root>
