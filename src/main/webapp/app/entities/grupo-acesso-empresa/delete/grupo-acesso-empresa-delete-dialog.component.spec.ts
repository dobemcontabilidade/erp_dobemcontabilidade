jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { GrupoAcessoEmpresaService } from '../service/grupo-acesso-empresa.service';

import { GrupoAcessoEmpresaDeleteDialogComponent } from './grupo-acesso-empresa-delete-dialog.component';

describe('GrupoAcessoEmpresa Management Delete Component', () => {
  let comp: GrupoAcessoEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<GrupoAcessoEmpresaDeleteDialogComponent>;
  let service: GrupoAcessoEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(GrupoAcessoEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GrupoAcessoEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GrupoAcessoEmpresaService);
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
