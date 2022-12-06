{ sources ? import ./nix/sources.nix { } }:
let pkgs = import sources.nixpkgs { };
in pkgs.mkShell rec { buildInputs = with pkgs; [ jdk17_headless nixfmt ]; }