import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import {Form,FormFeedback,Row,Alert,Col,Input,FormGroup,Label,Card,CardBody,} from "reactstrap";
import MatButton from "@material-ui/core/Button";
import SaveIcon from "@material-ui/icons/Save";
import { toast} from "react-toastify";
import { token, url } from "../../../../api";

const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    },
    cardBottom: {
        marginBottom: 20
    },
    Select: {
        height: 45,
        width: 350
    },
    button: {
        margin: theme.spacing(1)
    },

    root: {
        '& > *': {
            margin: theme.spacing(1)
        }
    },
    input: {
        border:'1px solid #014d88',
        borderRadius:'0px',
        fontSize:'14px',
        color:'#000'
    },
    arial: {
        border:'2px solid #014d88',
        borderRadius:'0px',
        fontSize:'15px',
        color:'#000'
    },
    error: {
        color: "#f85032",
        fontSize: "11px",
    },
    success: {
        color: "#4BB543 ",
        fontSize: "11px",
    },
    inputGroupText:{
        backgroundColor:'#014d88',
        fontWeight:"bolder",
        color:'#fff',
        borderRadius:'0px'
    },
   label:{
       fontSize:'14px',
       color:'#014d88',
       fontWeight:'bold'
   }
}))

function LabCodeSetting(props) {
    const classes = useStyles()
    const [otherfields, setOtherFields] = useState({
        lab_name:"",
        lab_code: ""
    });

    const [errors, setErrors] = useState({});

    const handleOtherFieldInputChange = (e) => {
        setOtherFields({ ...otherfields, [e.target.name]: e.target.value });
    };

    const validate = () => {
    let temp = { ...errors };

    temp.lab_name = otherfields.lab_name
        ? ""
        : "Lab name is required";
    temp.lab_code = otherfields.lab_code
        ? ""
        : "Lab code is required";
     setErrors({
                 ...temp,
             });
     return Object.values(temp).every((x) => x == "");

    }

    const saveSample = async (e) => {
        e.preventDefault();
         try {
            console.log("clicked")
            if (validate()) {
            console.log("submitted", otherfields)
            }

         } catch (e) {

            toast.error("An error occurred while saving lab settings", {
                 position: toast.POSITION.TOP_RIGHT
             });
         }
    };

  return (
    <>
        <Form onSubmit={saveSample}>
             <Row >
                <Col md={6}>
                    <FormGroup>
                        <Label for='lab_name' className={classes.label}>Laboratory Name</Label>

                        <Input
                            className={classes.input}
                            type="text"
                            name="lab_name"
                            id="lab_name"
                            onChange={handleOtherFieldInputChange}
                            value={otherfields.lab_name}
                        />

                       {errors.lab_name !="" ? (
                            <span className={classes.error}>{errors.lab_name}</span>
                        ) : "" }
                    </FormGroup>
                </Col>
                  <Col md={6}>
                    <FormGroup>
                        <Label for='lab_code' className={classes.label}>Laboratory Code</Label>

                        <Input
                            className={classes.input}
                            type="text"
                            name="lab_code"
                            id="lab_code"
                            onChange={handleOtherFieldInputChange}
                            value={otherfields.lab_code}
                        />

                       {errors.lab_code !="" ? (
                            <span className={classes.error}>{errors.lab_code}</span>
                        ) : "" }
                    </FormGroup>
                </Col>
               <Col>
               <MatButton
                    type="submit"
                    variant="contained"
                    color="primary"
                    className={classes.button}
                    startIcon={<SaveIcon />}
                >
                    Save
                </MatButton>
               </Col>
             </Row>
        </Form>
    </>
  );
}

export default LabCodeSetting;