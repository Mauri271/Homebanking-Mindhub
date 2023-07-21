const {createApp} = Vue;

const app = createApp({
    data() {
    return {
          accounts:{},
          transferObject:{
            originAccount: "",
            destinationAccount:"",
            amount:0,
            description:""
          }
        };
    },
  
    created(){
     this.accountsData()
     this.accounts.balance
    },
  
    methods:{
  //logout
    logOut(){
      axios.post('/api/logout').
      then(response => window.location.href="../htmlPages/index.html")
    },


    //get accounts data
    accountsData(){{
      axios.get("/api/clients/current")
      .then(res=> {
        
        this.accounts = res.data.accounts
        console.log(this.accounts)
      }).catch(err => console.log(err))
    }},


    //transfer money method
    transfer(){
      console.log(this.transferObject.originAccount, this.transferObject.destinationAccount, this.transferObject.amount, this.transferObject.description)

      axios.post('/api/transactions', this.transferObject)
      .then(res => console.log(res))
      .catch(err => console.log(err))
    },

    //alerts
    isTheclientSure(){
      Swal.fire({
          title: 'Are you sure?',
          text: "You won't be able to revert this!",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Yes!'
        }).then((result) => {
          if (result.isConfirmed) {
            Swal.fire(
              'Transfered!',
              'Money sent succesfully.',
              'success',
              this.transfer()
            )
          }
        })
  },
    wrongTransfer(){

      const selectedAccount = this.accounts.find(account =>  account.number == this.transferObject.originAccount).balance

    
      if(this.transferObject.originAccount == "" && this.transferObject.destinationAccount == "" && this.transferObject.amount == "" && this.transferObject.description == ""){
          Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Please fill all the fields',       
              background:'#0e0747',
              color:'#ffffff'
            })
      }
      else if(this.transferObject.originAccount == ""){
          Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Please choose an origin account',       
              background:'#0e0747',
              color:'#ffffff'
            })
      }
      else if(this.transferObject.destinationAccount == ""){
          Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Please choose a destination account',       
              background:'#0e0747',
              color:'#ffffff'
            })
      }

      else if(this.transferObject.amount == ""){
          Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'The amount cant be 0',       
              background:'#0e0747',
              color:'#ffffff'
            })
      }

      else if(this.transferObject.amount < 0){
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'The amount most be greater than 0',       
            background:'#0e0747',
            color:'#ffffff'
          })
    }
      else if(this.transferObject.amount > selectedAccount){
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Insuficient funds',       
            background:'#0e0747',
            color:'#ffffff'
          })
    }

      
      else if(this.transferObject.description == ""){
          Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: `Please set a description` ,       
              background:'#0e0747',
              color:'#ffffff'
            })
      } 
      else if(this.payments == ""){
          Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Please choose your payments',       
              background:'#0e0747',
              color:'#ffffff'
            })
      }else{
          this.isTheclientSure()
      }

      },


  
    
  
    }
  
  }).mount('#app')