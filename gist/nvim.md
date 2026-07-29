Basic NVIM config. Linux (`~/.config/init.vim`) Windows (`~\AppData\Local\nvim\init.vim`)

- Install NeoVim
- Install Vim-Plug (Minimal Plugin Manager)
- Keep the `init.vim` in the mentioned folder

```bash
set nocompatible            " disable compatibility to old-time vi
set showmatch               " show matching 
set ignorecase              " case insensitive 
set mouse=v                 " middle-click paste with 
set hlsearch                " highlight search 
set incsearch               " incremental search
set tabstop=4               " number of columns occupied by a tab 
set softtabstop=4           " see multiple spaces as tabstops so <BS> does the right thing
set expandtab               " converts tabs to white space
set shiftwidth=4            " width for autoindents
set autoindent              " indent a new line the same amount as the line just typed
set number                  " Show current line number
set relativenumber          " Show relative line numbers
set wildmode=longest,list   " get bash-like tab completions
filetype plugin indent on   " allow auto-indenting depending on file type
syntax on                   " syntax highlighting
set mouse=a                 " enable mouse click
set clipboard=unnamedplus   " using system clipboard
filetype plugin on
set ttyfast                 " Speed up scrolling in Vim
set spell                   " enable spell check (may need to download language package)
set noswapfile              " disable creating swap file
set backupdir=~/.cache/vim  " Directory to store backup files.
set nospell                 " No spell disable
colorscheme evening         " Color theme
call plug#begin()
   Plug 'ziglang/zig.vim'
   Plug 'nvim-lua/plenary.nvim'
   Plug 'nvim-telescope/telescope.nvim', { 'tag': '0.1.2' }
   Plug 'fatih/vim-go', { 'do': ':GoUpdateBinaries' }
   Plug 'neoclide/coc.nvim', {'branch': 'release'}
   Plug 'nvim-treesitter/nvim-treesitter', {'do': ':TSUpdate'}
   Plug 'mg979/vim-visual-multi', {'branch': 'master'}
   Plug 'itchyny/lightline.vim'
call plug#end()

nnoremap <leader>ff <cmd>Telescope find_files<cr>
nnoremap <leader>fg <cmd>Telescope live_grep<cr>
nnoremap <leader>fb <cmd>Telescope buffers<cr>
nnoremap <leader>fh <cmd>Telescope help_tags<cr>
inoremap <expr> <cr> ((pumvisible())?("\<C-y>"):("\<cr>"))

```
