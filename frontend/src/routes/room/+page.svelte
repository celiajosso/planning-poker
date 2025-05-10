<script lang="ts">
	import { onMount } from 'svelte';
	import { Icon } from '@steeze-ui/svelte-icon';
	import { ArrowPath, ArrowRightEndOnRectangle, ChevronDoubleLeft, ArrowDownOnSquare, ArrowUpOnSquare, Plus } from '@steeze-ui/heroicons';
	import { Game } from '$lib/socket.svelte';
	import { goto } from '$app/navigation';

	let canvas: HTMLCanvasElement;

	let loaded = $state(false);

	onMount(async () => {
		if (Game.socket === undefined) {
			goto('/');
		}
		else {
			await Game.init(canvas);
			loaded = true;
		}
	});

 import * as Table from "$lib/components/ui/table/index.js";
 
 const invoices = [
  {
   invoice: "INVd001",
   paymentStatus: "Paid",
   totalAmount: "$250.00",
   paymentMethod: "Credit Card"
  },
  {
   invoice: "INV002",
   paymentStatus: "Pending",
   totalAmount: "$150.00",
   paymentMethod: "PayPal"
  },
  {
   invoice: "INV003",
   paymentStatus: "Unpaid",
   totalAmount: "$350.00",
   paymentMethod: "Bank Transfer"
  },
  {
   invoice: "INV004",
   paymentStatus: "Paid",
   totalAmount: "$450.00",
   paymentMethod: "Credit Card"
  },
  {
   invoice: "INV005",
   paymentStatus: "Paid",
   totalAmount: "$550.00",
   paymentMethod: "PayPal"
  },
  {
   invoice: "INV006",
   paymentStatus: "Pending",
   totalAmount: "$200.00",
   paymentMethod: "Bank Transfer"
  },
  {
   invoice: "INV007",
   paymentStatus: "Unpaid",
   totalAmount: "$300.00",
   paymentMethod: "Credit Card"
  }
 ];
 
 import * as Sheet from "$lib/components/ui/sheet/index.js";
 import { Button } from "$lib/components/ui/button/index.js";
 import { Input } from "$lib/components/ui/input/index.js";
 import { Label } from "$lib/components/ui/label/index.js";
</script>

<canvas bind:this={canvas}></canvas>
{#if loaded}
	<Sheet.Root>
	<Sheet.Trigger asChild let:builder>
	<Button builders={[builder]} variant="" class="absolute top-4 right-4 z-50">	
		<Icon class="color-gray-900 size-8" src={ChevronDoubleLeft} theme="solid" />
	</Button>

	</Sheet.Trigger>
	<Sheet.Content side="right" class="bg-white">
	<Sheet.Header>
	<Sheet.Title>Issues</Sheet.Title>
	<Sheet.Description>
		Make changes to your User Stories. Click save when you're done.
	</Sheet.Description>

	</Sheet.Header>
	<div class="grid gap-4 py-4">
			<!-- <Button builders={[builder]} variant="" class="absolute top-4 right-4 z-50">	
		<Icon class="color-gray-900 size-8" src={ArrowDownOnSquare} theme="solid" />
	</Button>
		<Button builders={[builder]} variant="" class="absolute top-4 right-4 z-50">	
		<Icon class="color-gray-900 size-8" src={ArrowUpOnSquare} theme="solid" />
	</Button> -->
	<Table.Root>
	<Table.Header>
	<Table.Row>
	<Table.Head class="w-[100px]">Title</Table.Head>
	<Table.Head>Description</Table.Head>
	<Table.Head>Score</Table.Head>
	<Table.Head class="text-right">ICONES</Table.Head>
	</Table.Row>
	</Table.Header>
	<Table.Body>
	{#each invoices as invoice, i (i)}
	<Table.Row>
		<Table.Cell class="font-medium">{invoice.invoice}</Table.Cell>
		<Table.Cell>{invoice.paymentStatus}</Table.Cell>
		<Table.Cell>{invoice.paymentMethod}</Table.Cell>
		<Table.Cell class="text-right">{invoice.totalAmount}</Table.Cell>
	</Table.Row>
	{/each}
	</Table.Body>
	</Table.Root>
	</div>
	<Sheet.Footer>
	<Button builders={[]} class="outline items-center">
		<Icon class="color-gray-900 size-8" src={ChevronDoubleLeft} theme="solid" />
		Add an issue
	</Button>
	<Sheet.Close asChild let:builder>
		<Button builders={[builder]} type="submit">Save changes</Button>
	</Sheet.Close>
	</Sheet.Footer>
	</Sheet.Content>
	</Sheet.Root>
	<div class="absolute left-0 bottom-4 flex justify-center items-center w-full">
		<div class="bg-white rounded-xl flex px-4 py-2 gap-4 flex-col">
			<input bind:value={Game.user.username} type="text" />
			<input bind:value={Game.room.id} type="text" />
			<div class="flex items-center justify-center">
				<button onclick={() => Game.restart()}>
					<Icon class="color-gray-900 size-8" src={ArrowPath} theme="solid" />
				</button>
				<button onclick={() => Game.quitRoom()}>
					<Icon class="color-gray-900 size-8" src={ArrowRightEndOnRectangle} theme="solid" />
				</button>
			</div>
		</div>
	</div>
{/if}
