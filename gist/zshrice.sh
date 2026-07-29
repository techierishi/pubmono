# Install Firacode fonts
# https://www.nerdfonts.com/

# Install starship
curl -sS https://starship.rs/install.sh | sh
starship preset catppuccin-powerline -o ~/.config/starship.toml

# Install omz
sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"

# Install omz plugin
sudo apt install zsh-autosuggestions zsh-syntax-highlighting zsh

git clone https://github.com/zsh-users/zsh-autosuggestions.git $ZSH_CUSTOM/plugins/zsh-autosuggestions
git clone https://github.com/zsh-users/zsh-syntax-highlighting.git $ZSH_CUSTOM/plugins/zsh-syntax-highlighting
git clone https://github.com/zdharma-continuum/fast-syntax-highlighting.git ${ZSH_CUSTOM:-$HOME/.oh-my-zsh/custom}/plugins/fast-syntax-highlighting
git clone --depth 1 -- https://github.com/marlonrichert/zsh-autocomplete.git $ZSH_CUSTOM/plugins/zsh-autocomplete


# ctrl+e+x to edit current command in vim

# .zshrc
eval "$(starship init zsh)"

plugins=(git zsh-autosuggestions zsh-syntax-highlighting fast-syntax-highlighting zsh-autocomplete)
source $ZSH/oh-my-zsh.sh
