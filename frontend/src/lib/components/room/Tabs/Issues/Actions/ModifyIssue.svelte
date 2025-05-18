<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { PencilSquare } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import * as Select from "$lib/components/ui/select/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
  import { ScrollArea } from "$lib/components/ui/scroll-area";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  import { scores } from "../../../../../../routes/room/script";
  import FormRow from "../Form/FormRow.svelte";
  import FormRowScore from "../Form/FormRowScore.svelte";

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
        <FormRow field="title" value={selectedIssue!.title} />
        <FormRow field="description" value={selectedIssue!.description} />
        <FormRowScore />
      </div>
      <Dialog.Footer>
        <Button type="submit">Save changes</Button>
      </Dialog.Footer>
    </form>
  </Dialog.Content>
</Dialog.Root>
