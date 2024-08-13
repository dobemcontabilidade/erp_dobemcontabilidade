import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPermisao } from '../permisao.model';
import { PermisaoService } from '../service/permisao.service';
import { PermisaoFormService, PermisaoFormGroup } from './permisao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-permisao-update',
  templateUrl: './permisao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PermisaoUpdateComponent implements OnInit {
  isSaving = false;
  permisao: IPermisao | null = null;

  protected permisaoService = inject(PermisaoService);
  protected permisaoFormService = inject(PermisaoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PermisaoFormGroup = this.permisaoFormService.createPermisaoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ permisao }) => {
      this.permisao = permisao;
      if (permisao) {
        this.updateForm(permisao);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const permisao = this.permisaoFormService.getPermisao(this.editForm);
    if (permisao.id !== null) {
      this.subscribeToSaveResponse(this.permisaoService.update(permisao));
    } else {
      this.subscribeToSaveResponse(this.permisaoService.create(permisao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermisao>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(permisao: IPermisao): void {
    this.permisao = permisao;
    this.permisaoFormService.resetForm(this.editForm, permisao);
  }
}
