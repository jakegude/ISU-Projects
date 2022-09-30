README

Jake Gudenkauf

first version includes
    "reset_requested" and "reset_finished" are written
    check for number of cmd line arguments
    assigns inputbuffer and outputbuffer number
    then reads input in single thread and writes output to outputfile

final version includes 
    "reset_requested" and "reset_finished" are written
    check for number of cmd line arguments
    assigns inputbuffer and outputbuffer number
    then creates each thread, (read, input count, encrypt, output count, write)
    joins each together with main thread

    in my testing i had no luck getting any output, pretty much ever,
    unless it was a fairly short string of random letters.
    as you can tell from the commented code i had tried different 
    ways of doing this project (with mutexes and condition variables, 
    and also semaphores as well as different numbers of semaphores). 
    I ended up using 8 semaphores because thats what I ended up 
    thinking made the most sense, and was still fairly simple conceptually
    I am also testing on a Mac so i'm not sure if that made any difference
    Hoping this reaches the grader well and not graded to harshly, thank you
