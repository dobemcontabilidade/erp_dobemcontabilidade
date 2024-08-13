jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FuncionalidadeGrupoAcessoPadraoService } from '../service/funcionalidade-grupo-acesso-padrao.service';

import { FuncionalidadeGrupoAcessoPadraoDeleteDialogComponent } from './funcionalidade-grupo-acesso-padrao-delete-dialog.component';

describe('FuncionalidadeGrupoAcessoPadrao Management Delete Component', () => {
  let comp: FuncionalidadeGrupoAcessoPadraoDeleteDialogComponent;
  let fixture: ComponentFixture<FuncionalidadeGrupoAcessoPadraoDeleteDialogComponent>;
  let service: FuncionalidadeGrupoAcessoPadraoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuncionalidadeGrupoAcessoPadraoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FuncionalidadeGrupoAcessoPadraoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FuncionalidadeGrupoAcessoPadraoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FuncionalidadeGrupoAcessoPadraoService);
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
