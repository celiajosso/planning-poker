<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import ImportButton from "$lib/components/room/Tabs/Issues/Actions/ImportButton.svelte";
  import ExportButton from "$lib/components/room/Tabs/Issues/Actions/ExportButton.svelte";
  import AddIssue from "$lib/components/room/Tabs/Issues/Actions/AddIssue.svelte";
  import TableHeader from "./Issues/Table/TableHeader.svelte";
  import TableRow from "./Issues/Table/TableRow.svelte";
  import * as Tabs from "$lib/components/ui/tabs/index.js";
  import * as Table from "$lib/components/ui/table/index.js";
  import * as Dialog from "$lib/components/ui/dialog";
  import FormRow from "./Issues/Form/FormRow.svelte";
  import FormRowScore from "./Issues/Form/FormRowScore.svelte";
  import SubmitButton from "./Issues/Form/SubmitButton.svelte";
  import * as AlertDialog from "$lib/components/ui/alert-dialog";

  let isModifyOpen = $state(false);
  let isAddOpen = $state(false);
  let isDeleteOpen = $state(false);
  let selectedIssue: StoryDTO | undefined = $state();
</script>

<Tabs.Content value="issues">
  <div class="grid gap-2 p-5 my-5 border border-gray-300 rounded-lg">
    <div class="flex flex-row justify-between items-center">
      <div>
        <p class="font-bold text-xl">Issues</p>
        <p class="text-sm">Make changes to your User Stories.</p>
      </div>
      <div class="flex flex-row items-center h-full gap-2">
        <ImportButton />
        <ExportButton />
      </div>
    </div>
    {#if Game.storage.room.stories && Game.storage.room.stories.length != 0}
      <Table.Root>
        <TableHeader />
        <Table.Body>
          {#each Game.storage.room.stories as issue}
            <TableRow
              bind:isModifyOpen
              bind:isDeleteOpen
              {issue}
              bind:selectedIssue
            />
          {/each}
        </Table.Body>
      </Table.Root>
    {:else}
      <p class="text-center w-full text-gray-500">No issue</p>
    {/if}
    <AddIssue {isAddOpen} />
    <AlertDialog.Root bind:open={isDeleteOpen}>
      <AlertDialog.Content>
        <AlertDialog.Header>
          <AlertDialog.Title>Are you absolutely sure?</AlertDialog.Title>
          <AlertDialog.Description>
            This action cannot be undone. This will permanently delete your
            issue.
          </AlertDialog.Description>
        </AlertDialog.Header>
        <AlertDialog.Footer>
          <AlertDialog.Cancel>Cancel</AlertDialog.Cancel>

          <AlertDialog.Action
            onclick={() => {
              Game.deleteStory(selectedIssue!.id);
              isDeleteOpen = false;
            }}>Continue</AlertDialog.Action
          >
        </AlertDialog.Footer>
      </AlertDialog.Content>
    </AlertDialog.Root>
    <Dialog.Root bind:open={isModifyOpen}>
      <Dialog.Content class="sm:max-w-[425px]">
        <Dialog.Header>
          <Dialog.Title>Edit issue</Dialog.Title>
          <Dialog.Description>
            Make changes to your issue here. Click save when you're done.
          </Dialog.Description>
        </Dialog.Header>
        <form
          class="grid gap-4 py-4"
          onsubmit={(e) => {
            Game.updateStory(selectedIssue!.id, e);
            isModifyOpen = false;
          }}
        >
          <FormRow field="title" value={selectedIssue!.title} />
          <FormRow field="description" value={selectedIssue!.description} />
          <FormRowScore value={selectedIssue!.finalEstimate} />
          <SubmitButton />
        </form>
      </Dialog.Content>
    </Dialog.Root>
  </div>
</Tabs.Content>
