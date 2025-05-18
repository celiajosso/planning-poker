<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Plus } from "@steeze-ui/heroicons";

  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import { Button } from "$lib/components/ui/button/index.js";

  import FormRow from "../Form/FormRow.svelte";
  import FormRowScore from "../Form/FormRowScore.svelte";
  import SubmitButton from "../Form/SubmitButton.svelte";

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
          <FormRow field="title" />
          <FormRow field="description" />
          <FormRowScore />
        </div>
        <SubmitButton />
      </Dialog.Content>
    </form></Dialog.Root
  >
</div>
