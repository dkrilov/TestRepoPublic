## Welcome to Dimitry's Page

You can use the [editor on GitHub](https://github.com/dkrilov/TestRepoPublic/edit/master/README.md) to maintain and preview the content for your website in Markdown files.

Whenever you commit to this repository, GitHub Pages will run [Jekyll](https://jekyllrb.com/) to rebuild the pages in your site, from the content in your Markdown files.

### Markdown

For more details see [GitHub Flavored Markdown](https://guides.github.com/features/mastering-markdown/).

### Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/dkrilov/TestRepoPublic/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

# Cypress Test Project

## Installing Dependencies

```sh
yarn install
```

## Running Test Manually in Development Environment

```sh
bash run-test.sh
```

## Running Test in Jenkins Pipeline

```sh
steps {
  browserstack(
    credentialsId: 'browserstack-jenkins-creds',
    localConfig: [
      localPath: '',
      localIdentifier: null
    ]
  ) {
    sh """
      yarn browserstack-cypress run --sync
    """
  })
}
...
```

## Running tests in browserStack

```sh
browserstack-cypress run --sync --u $your_username --k $Access_key
```
