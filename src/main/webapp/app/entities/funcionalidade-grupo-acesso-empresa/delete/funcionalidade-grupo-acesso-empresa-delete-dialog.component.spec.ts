jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FuncionalidadeGrupoAcessoEmpresaService } from '../service/funcionalidade-grupo-acesso-empresa.service';

import { FuncionalidadeGrupoAcessoEmpresaDeleteDialogComponent } from './funcionalidade-grupo-acesso-empresa-delete-dialog.component';

describe('FuncionalidadeGrupoAcessoEmpresa Management Delete Component', () => {
  let comp: FuncionalidadeGrupoAcessoEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<FuncionalidadeGrupoAcessoEmpresaDeleteDialogComponent>;
  let service: FuncionalidadeGrupoAcessoEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuncionalidadeGrupoAcessoEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FuncionalidadeGrupoAcessoEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FuncionalidadeGrupoAcessoEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FuncionalidadeGrupoAcessoEmpresaService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
