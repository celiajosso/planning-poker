<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Plus } from "@steeze-ui/heroicons";
  import { Button } from "$lib/components/ui/button/index.js";
  import * as Dialog from "$lib/components/ui/dialog";

  import FormRow from "../Form/FormRow.svelte";
  import SubmitButton from "../Form/SubmitButton.svelte";

  let { isAddOpen = $bindable() } = $props();
</script>

<div class="flex justify-center w-full">
  <Dialog.Root bind:open={isAddOpen}>
    <Dialog.Trigger>
      <Button
        class="outline flex flex-row gap-1 p-4 mx-auto m-2"
        onclick={() => (isAddOpen = true)}
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
      <form
        class="grid gap-4 py-4"
        onsubmit={(e) => {
          Game.createStory(e);
          isAddOpen = false;
        }}
      >
        <FormRow field="title" />
        <FormRow field="description" />
        <SubmitButton />
      </form>
    </Dialog.Content>
  </Dialog.Root>
</div>
