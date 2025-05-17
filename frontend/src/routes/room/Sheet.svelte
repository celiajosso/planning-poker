<script lang="ts">
    import * as Sheet from "$lib/components/ui/sheet/index.js";
    import { exportToCSV } from "$lib/CSV.svelte";
    import {
        ChevronDoubleLeft,
        ArrowDownTray,
        ArrowUpTray,
        Plus,
        Trash,
        PaperAirplane,
        PencilSquare,
        Check,
    } from "@steeze-ui/heroicons";
    import * as Table from "$lib/components/ui/table/index.js";
    import * as Tabs from "$lib/components/ui/tabs/index.js";
    import * as AlertDialog from "$lib/components/ui/alert-dialog/index.js";

    import { handleFileChange } from "./+page";
    import ButtonIcon from "$lib/ButtonIcon.svelte";
    import { Game } from "$lib/Game.svelte";
    import * as Tooltip from "$lib/components/ui/tooltip/index.js";
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import { Button } from "$lib/components/ui/button/index.js";
    import { Icon } from "@steeze-ui/svelte-icon";
    import { Label } from "$lib/components/ui/label";
    import { Input } from "$lib/components/ui/input";
    import * as Select from "$lib/components/ui/select/index.js";
    import { ScrollArea } from "$lib/components/ui/scroll-area";

    let { isModifyOpen, selectedIssue, isAddOpen } = $props();

    let fileInput: HTMLInputElement;

    import { scores } from "./+page";
</script>

<Sheet.Root>
    <Sheet.Trigger class="absolute top-5 right-5 color-gray-700">
        <ButtonIcon icon={ChevronDoubleLeft} size="6" theme="solid"
        ></ButtonIcon>
    </Sheet.Trigger>
    <Sheet.Content side="right" class="bg-[#f4f4f9] text-gray-900">
        <Tabs.Root value="profile-settings" class="w-full">
            <Tabs.List
                class=" w-full flex bg-gray-200 mt-5 justify-between gap-1"
            >
                <Tabs.Trigger
                    class="font-semibold bg-white flex-1"
                    value="profile-settings">Profile Settings</Tabs.Trigger
                >
                {#if Game.storage.user.role == "Administrator"}
                    <Tabs.Trigger
                        class="font-semibold  bg-white flex-1"
                        value="issues">Issues</Tabs.Trigger
                    >
                {/if}
            </Tabs.List>
            {#if Game.storage.user.role == "Administrator"}
                <Tabs.Content value="issues">
                    <div
                        class="grid gap-2 p-5 my-5 border border-gray-300 rounded-lg"
                    >
                        <div class="flex flex-row justify-between items-center">
                            <div>
                                <p class="font-bold text-xl">Issues</p>
                                <p class="text-sm">
                                    Make changes to your User Stories.
                                </p>
                            </div>
                            <div
                                class="flex flex-row items-center h-full gap-2"
                            >
                                <Tooltip.Root>
                                    <Tooltip.Trigger>
                                        <ButtonIcon
                                            onclick={fileInput.click}
                                            icon={ArrowUpTray}
                                            size="5"
                                            theme="solid"
                                        ></ButtonIcon>
                                    </Tooltip.Trigger>
                                    <Tooltip.Content>
                                        <p>Import CSV file</p>
                                    </Tooltip.Content>
                                </Tooltip.Root>
                                <input
                                    bind:this={fileInput}
                                    type="file"
                                    accept=".csv"
                                    onchange={handleFileChange}
                                    style="display: none;"
                                />
                                <Tooltip.Root>
                                    <Tooltip.Trigger>
                                        <ButtonIcon
                                            onclick={exportToCSV}
                                            icon={ArrowDownTray}
                                            size="5"
                                            theme="solid"
                                        ></ButtonIcon>
                                    </Tooltip.Trigger>
                                    <Tooltip.Content>
                                        <p>Export CSV file</p>
                                    </Tooltip.Content>
                                </Tooltip.Root>
                            </div>
                        </div>
                        {#if Game.storage.room.stories && Game.storage.room.stories.length != 0}
                            <Table.Root>
                                <Table.Header>
                                    <Table.Row>
                                        <!-- <Table.Head class="text-center">
										<Checkbox
											id="terms"
											bind:checked
											aria-labelledby="terms-label"
											class="mx-auto"
										/>
									</Table.Head> -->
                                        <Table.Head>Title</Table.Head>
                                        <Table.Head>Description</Table.Head>
                                        <Table.Head>Score</Table.Head>
                                        <Table.Head></Table.Head>
                                    </Table.Row>
                                </Table.Header>

                                <Table.Body>
                                    {#each Game.storage.room.stories as issue}
                                        <Table.Row>
                                            <!--<Table.Cell>
											 <Checkbox
												id="terms"
												bind:checked
												aria-labelledby="terms-label"
											/> 
										</Table.Cell>-->
                                            <Table.Cell
                                                >{issue.title}</Table.Cell
                                            >
                                            <Table.Cell>
                                                <p class="truncate max-w-96">
                                                    {issue.description}
                                                </p></Table.Cell
                                            >
                                            <Table.Cell>
                                                <!-- <Select.Root>
                        <Select.Trigger class="w-[100px]">
                          <Select.Value placeholder="Score" />
                        </Select.Trigger>
                        <Select.Content>
                          <Select.Group>
                            <ScrollArea class="h-20">
                              {#each scores as score}
                                <Select.Item
                                  value={score.value}
                                  label={score.value}>{score.value}</Select.Item
                                >
                              {/each}
                            </ScrollArea>
                          </Select.Group>
                        </Select.Content>
                        <Select.Input name="score" />
                      </Select.Root> -->
                                                {issue.finalEstimate}
                                            </Table.Cell>
                                            <Table.Cell>
                                                <div
                                                    class="flex flex-row items-center h-full gap-2"
                                                >
                                                    <AlertDialog.Root>
                                                        <AlertDialog.Trigger
                                                            ><Tooltip.Root>
                                                                <AlertDialog.Trigger
                                                                    asChild
                                                                >
                                                                    <Tooltip.Root
                                                                    >
                                                                        <Tooltip.Trigger
                                                                        >
                                                                            <ButtonIcon
                                                                                icon={Trash}
                                                                                size="5"
                                                                                theme="outline"

                                                                            ></ButtonIcon>
                                                                        </Tooltip.Trigger>
                                                                        <Tooltip.Content
                                                                        >
                                                                            <p>
                                                                                Delete
                                                                            </p>
                                                                        </Tooltip.Content>
                                                                    </Tooltip.Root>
                                                                </AlertDialog.Trigger>

                                                                <Tooltip.Content
                                                                >
                                                                    <p>
                                                                        Delete
                                                                    </p>
                                                                </Tooltip.Content>
                                                            </Tooltip.Root></AlertDialog.Trigger
                                                        >
                                                        <AlertDialog.Content>
                                                            <AlertDialog.Header>
                                                                <AlertDialog.Title
                                                                    >Are you
                                                                    sure?</AlertDialog.Title
                                                                >
                                                                <AlertDialog.Description
                                                                >
                                                                    This action
                                                                    cannot be
                                                                    undone. This
                                                                    will
                                                                    permanently
                                                                    delete your
                                                                    issue.
                                                                </AlertDialog.Description>
                                                            </AlertDialog.Header>
                                                            <AlertDialog.Footer>
                                                                <AlertDialog.Cancel
                                                                    >Cancel</AlertDialog.Cancel
                                                                >
                                                                <AlertDialog.Action
                                                                    onclick={() =>
                                                                        Game.deleteStory(
                                                                            issue.id,
                                                                        )}
                                                                    >Continue</AlertDialog.Action
                                                                >
                                                            </AlertDialog.Footer>
                                                        </AlertDialog.Content>
                                                    </AlertDialog.Root>
                                                    <Tooltip.Root>
                                                        <Tooltip.Trigger>
                                                            <ButtonIcon
                                                                onclick={() => {
                                                                    selectedIssue =
                                                                        issue;
                                                                    isModifyOpen = true;
                                                                }}
                                                                icon={PencilSquare}
                                                                size="5"
                                                                theme="outline"
                                                            ></ButtonIcon>
                                                        </Tooltip.Trigger>
                                                        <Tooltip.Content>
                                                            <p>Modify</p>
                                                        </Tooltip.Content>
                                                    </Tooltip.Root>
                                                    <Tooltip.Root>
                                                        <Tooltip.Trigger>
                                                            <ButtonIcon
                                                                onclick={() =>
                                                                    Game.selectStory(
                                                                        issue.id,
                                                                    )}
                                                                icon={PaperAirplane}
                                                                size="5"
                                                                theme="outline"
                                                            ></ButtonIcon>
                                                        </Tooltip.Trigger>
                                                        <Tooltip.Content>
                                                            <p>Vote</p>
                                                        </Tooltip.Content>
                                                    </Tooltip.Root>
                                                </div>
                                            </Table.Cell>
                                        </Table.Row>
                                    {/each}
                                </Table.Body>
                            </Table.Root>
                        {:else}
                            <p class="text-center w-full text-gray-500">
                                No issue
                            </p>
                        {/if}
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
                                            <Icon
                                                class="color-gray-800 size-5"
                                                src={Plus}
                                                theme="solid"
                                            />
                                            <div>Add an issue</div>
                                        </Button>
                                    </Dialog.Trigger>
                                    <Dialog.Content class="sm:max-w-[425px]">
                                        <Dialog.Header>
                                            <Dialog.Title
                                                >Add an issue</Dialog.Title
                                            >
                                            <Dialog.Description>
                                                Add an issue here. Click save
                                                when you're done.
                                            </Dialog.Description>
                                        </Dialog.Header>
                                        <div class="grid gap-4 py-4">
                                            <div
                                                class="grid grid-cols-4 items-center gap-4"
                                            >
                                                <Label
                                                    for="title"
                                                    class="text-right"
                                                    >Title</Label
                                                >
                                                <Input
                                                    id="title"
                                                    name="title"
                                                    class="col-span-3"
                                                    required
                                                />
                                            </div>
                                            <div
                                                class="grid grid-cols-4 items-center gap-4"
                                            >
                                                <Label
                                                    for="description"
                                                    class="text-right"
                                                    >Description</Label
                                                >
                                                <Input
                                                    id="description"
                                                    name="description"
                                                    class="col-span-3"
                                                    required
                                                />
                                            </div>
                                            <div
                                                class="grid grid-cols-4 items-center gap-4"
                                            >
                                                <Label
                                                    for="issue-description"
                                                    class="text-right"
                                                    >Score</Label
                                                >
                                                <Select.Root>
                                                    <Select.Trigger
                                                        class="w-[100px]"
                                                    >
                                                        <Select.Value
                                                            placeholder="Score"
                                                        />
                                                    </Select.Trigger>
                                                    <Select.Content>
                                                        <Select.Group>
                                                            <ScrollArea
                                                                class="h-20"
                                                            >
                                                                {#each scores as score}
                                                                    <Select.Item
                                                                        value={score.value}
                                                                        label={score.value}
                                                                        >{score.value}</Select.Item
                                                                    >
                                                                {/each}
                                                            </ScrollArea>
                                                        </Select.Group>
                                                    </Select.Content>
                                                    <Select.Input
                                                        name="score"
                                                    />
                                                </Select.Root>
                                            </div>
                                        </div>
                                        <Dialog.Footer>
                                            <Button type="submit"
                                                >Save changes</Button
                                            >
                                        </Dialog.Footer>
                                    </Dialog.Content>
                                </form></Dialog.Root
                            >
                        </div>
                    </div>
                </Tabs.Content>
            {/if}

            <Tabs.Content value="profile-settings"
                ><div
                    class="grid gap-2 p-5 my-5 border border-gray-300 rounded-lg"
                >
                    <div class="flex gap-4 flex-col">
                        <div class="flex flex-row justify-between items-center">
                            <div>
                                <p class="font-bold text-xl">
                                    Profile Settings
                                </p>
                                <p class="text-sm">
                                    Make changes to your Profile Settings.
                                </p>
                            </div>
                        </div>

                        <div class="flex gap-4 items-center">
                            <Label for="name"
                                ><p class="whitespace-nowrap">
                                    Username
                                </p></Label
                            >
                            <Input
                                id="name"
                                bind:value={Game.storage.user.username}
                                type="text"
                            />
                        </div>
                    </div>
                    <div class="flex justify-center w-full">
                        <Button
                            builders={[]}
                            class="outline flex flex-row gap-1 p-4 mx-auto m-2"
                            onclick={() => Game.updateUser(Game.storage.user)}
                        >
                            <Icon
                                class="color-gray-800 size-5"
                                src={Check}
                                theme="solid"
                            />
                            <div>Save</div>
                        </Button>
                    </div>
                </div></Tabs.Content
            >
        </Tabs.Root>
    </Sheet.Content>
</Sheet.Root>
