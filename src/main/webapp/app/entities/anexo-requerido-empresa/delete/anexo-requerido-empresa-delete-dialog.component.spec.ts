jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnexoRequeridoEmpresaService } from '../service/anexo-requerido-empresa.service';

import { AnexoRequeridoEmpresaDeleteDialogComponent } from './anexo-requerido-empresa-delete-dialog.component';

describe('AnexoRequeridoEmpresa Management Delete Component', () => {
  let comp: AnexoRequeridoEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<AnexoRequeridoEmpresaDeleteDialogComponent>;
  let service: AnexoRequeridoEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AnexoRequeridoEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnexoRequeridoEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnexoRequeridoEmpresaService);
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
