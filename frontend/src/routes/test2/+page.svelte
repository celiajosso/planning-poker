<script lang="ts">
 import * as Table from "$lib/components/ui/table/index.js";
 
 const invoices = [
  {
   invoice: "INV001",
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
 
<Sheet.Root>
 <Sheet.Trigger asChild let:builder>
  <Button builders={[builder]} variant="outline">Open</Button>
 </Sheet.Trigger>
 <Sheet.Content side="right">
  <Sheet.Header>
   <Sheet.Title>Edit profile</Sheet.Title>
   <Sheet.Description>
    Make changes to your profile here. Click save when you're done.
   </Sheet.Description>
  </Sheet.Header>
  <div class="grid gap-4 py-4">
   <div class="grid grid-cols-4 items-center gap-4">
    <Label for="name" class="text-right">Name</Label>
    <Input id="name" value="Pedro Duarte" class="col-span-3" />
   </div>
   <div class="grid grid-cols-4 items-center gap-4">
    <Label for="username" class="text-right">Username</Label>
    <Input id="username" value="@peduarte" class="col-span-3" />
   </div>
   <Table.Root>
 <Table.Caption>A list of your recent invoices.</Table.Caption>
 <Table.Header>
  <Table.Row>
   <Table.Head class="w-[100px]">Invoice</Table.Head>
   <Table.Head>Status</Table.Head>
   <Table.Head>Method</Table.Head>
   <Table.Head class="text-right">Amount</Table.Head>
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
   <Sheet.Close asChild let:builder>
    <Button builders={[builder]} type="submit">Save changes</Button>
   </Sheet.Close>
  </Sheet.Footer>
 </Sheet.Content>
</Sheet.Root>