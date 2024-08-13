jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { GrupoAcessoUsuarioContadorService } from '../service/grupo-acesso-usuario-contador.service';

import { GrupoAcessoUsuarioContadorDeleteDialogComponent } from './grupo-acesso-usuario-contador-delete-dialog.component';

describe('GrupoAcessoUsuarioContador Management Delete Component', () => {
  let comp: GrupoAcessoUsuarioContadorDeleteDialogComponent;
  let fixture: ComponentFixture<GrupoAcessoUsuarioContadorDeleteDialogComponent>;
  let service: GrupoAcessoUsuarioContadorService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoUsuarioContadorDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(GrupoAcessoUsuarioContadorDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GrupoAcessoUsuarioContadorDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GrupoAcessoUsuarioContadorService);
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
