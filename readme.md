# Welcome on this project



## Eclipse configuration 

### Tomcat deploy


## Git 

### Fork 

By default Git name the base of a clone : origin, and work every time on the checkout local branch. In a multi-repository flow, give the name of the cloud remote is a good practise.

- update from a specify remote and branch :

In this sample, `mphil78` is the remote, `Encheres-fin-1ere-semaine` is the branch

```Shell
git fetch --all
git pull --rebase mphil78 Encheres-fin-1ere-semaine
```

- backup on a cloud and specify 

```Shell
git push origin
```