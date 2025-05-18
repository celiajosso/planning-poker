<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Plus } from "@steeze-ui/heroicons";

  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";

  import ScoreSelection from "$lib/components/room/Tabs/Issues/ScoreSelection.svelte";
  import FormRow from "./components/FormRow.svelte";

  let { isAddOpen } = $props();
</script>

<div class="flex justify-center w-full">
  <Dialog.Root bind:open={isAddOpen}>
    <form
      onsubmit={(e) => {
        Game.createStory(e);
        isAddOpen = false;
      }}
    >
      <Dialog.Trigger>
        <Button
          onclick={() => (isAddOpen = true)}
          class="outline flex flex-row gap-1 p-4 mx-auto m-2"
        >
          <Icon class="color-gray-800 size-5" src={Plus} theme="solid" />
          <div>Add an issue</div>
        </Button>
      </Dialog.Trigger>
      <Dialog.Content class="sm:max-w-[425px]">
        <Dialog.Header>
          <Dialog.Title>Add an issue</Dialog.Title>
          <Dialog.Description>
            Add an issue here. Click save when you're done.
          </Dialog.Description>
        </Dialog.Header>
        <div class="grid gap-4 py-4">
          <FormRow field="Title" required />
          <FormRow field="Description" required />

          <div class="grid grid-cols-4 items-center gap-4">
            <Label for="issue-description" class="text-right">Score</Label>
            <ScoreSelection />
          </div>
        </div>
        <Dialog.Footer>
          <Button type="submit">Save changes</Button>
        </Dialog.Footer>
      </Dialog.Content>
    </form></Dialog.Root
  >
</div>
