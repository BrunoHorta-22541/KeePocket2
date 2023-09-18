@extends('layouts.auth');
@section('content')
<div class="main-panel">
    <div class="content-wrapper">
        <!--Conteudo Aqui -->
        <div class="row">
            <div class="col-lg-12 grid-margin stretch-card">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Categories</h4>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID Category</th>
                                        <th>Category Name</th>
                                        <th>Limit</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    @foreach ($category as $categories)
                                    <tr>
                                        <td>{{ $categories->id}}</td>
                                        <td>{{ $categories->categoryName}}</td>
                                        <td>{{ $categories->limit}}</td>
                                    </tr>
                                    @endforeach
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>

            </div>

        </div>

        <!--Conteudo Aqui -->
    </div>
    <!-- content-wrapper ends -->
    <!-- partial:partials/_footer.html -->

    <!-- partial -->
</div>
@endsection