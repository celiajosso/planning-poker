import { importCSV } from "$lib/CSV.svelte";
import { Game } from "$lib/Game.svelte";
import { toast } from "svelte-sonner";

export function handleFileChange(event) {
  const file = event.target.files[0];
  if (
    file &&
    (file.type === "text/csv" || file.type === "application/vnd.ms-excel")
  ) {
    importCSV(file);
  } else {
    alert("Please selectt a CSV file.");
  }
}

export function copyRoomIdClipboard() {
  navigator.clipboard.writeText(Game.storage.room.id).then(
    function () {
      toast("Room id copied!");
    },
    function (err) {
      toast("Async: Could not copy text: ", err);
    },
  );
}

export function copyRoomLinkClipboard() {
  navigator.clipboard
    .writeText(`${window.location.origin}?roomId=${Game.storage.room.id}`)
    .then(
      function () {
        toast("Room link copied!");
      },
      function (err) {
        toast("Async: Could not copy text: ", err);
      },
    );
}

export const scores = Array.from({ length: 14 }, (_, i) => ({
  value: i.toString(),
  label: i.toString(),
}));