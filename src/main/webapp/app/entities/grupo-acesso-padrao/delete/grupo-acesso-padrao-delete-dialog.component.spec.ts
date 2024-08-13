jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { GrupoAcessoPadraoService } from '../service/grupo-acesso-padrao.service';

import { GrupoAcessoPadraoDeleteDialogComponent } from './grupo-acesso-padrao-delete-dialog.component';

describe('GrupoAcessoPadrao Management Delete Component', () => {
  let comp: GrupoAcessoPadraoDeleteDialogComponent;
  let fixture: ComponentFixture<GrupoAcessoPadraoDeleteDialogComponent>;
  let service: GrupoAcessoPadraoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoPadraoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(GrupoAcessoPadraoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GrupoAcessoPadraoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GrupoAcessoPadraoService);
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
