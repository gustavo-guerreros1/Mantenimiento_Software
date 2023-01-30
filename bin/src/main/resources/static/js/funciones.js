
function eliminar(id) {
	/*
		swal({
			title: "Desea eliminar el producto?",
			text: "Once deleted, you will not be able to recover this imaginary file!",
			icon: "warning",
			buttons: true,
			dangerMode: true,
		})
			.then((willDelete) => {
				if (willDelete) {
					$.ajax({
						url: "/delete/" + id,
						success: function(res) {
							console.log(res);
						}
					});
					swal("Poof! Your imaginary file has been deleted!", {
						icon: "success",
					})
						.then((willDelete) => {
							if (willDelete) {
								location.href = "/carrito";
							}
						});
				} else {
					swal("Your imaginary file is safe!");
				}
			});
	*/
	Swal.fire({
		title: '¿Desea eliminar el producto del carrito?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, eliminar'
	}).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url: "/delete/" + id,
				success: function(res) {
					console.log(res);
				}
			});
			Swal.fire(
				'Eliminado',
				'Producto eliminado del carrito',
				'success'
			)
				.then((result) => {
					if (result.isConfirmed) {
						location.href = "/carrito";
					}
				});

		}
	})

}

function eliminarProducto(id) {
	
	Swal.fire({
		title: '¿Desea eliminar el producto?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, eliminar'
	}).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url: "/admin/deleteProducto/" + id,
				
			});
			Swal.fire(
				'Eliminado',
				'Producto eliminado',
				'success'
			)
				.then((result) => {
					if (result.isConfirmed) {
						location.href = "/admin/productosPage";
					}
				});

		}
	})

}

function eliminarAdministrador(id) {
	
	Swal.fire({
		title: '¿Desea eliminar el administrador?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, eliminar'
	}).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url: "/admin/deleteAdministrador/" + id,
				success: function(res) {
					console.log(res);
				}
			});
			Swal.fire(
				'Eliminado',
				'Usuario] eliminado',
				'success'
			)
				.then((result) => {
					if (result.isConfirmed) {
						location.href = "/admin/usuarios";
					}
				});

		}
	})

}

function procesar(nombre) {
	
	Swal.fire(
		'Compra exitosa',
		'Estimado '+nombre+" su compra fue realizada con exito!, puede verificar su compra la seccion de mis compras",
		'success'
	).then((result) => {
					if (result.isConfirmed) {
						location.href = "/procesarcompra";
					}
			});
}

