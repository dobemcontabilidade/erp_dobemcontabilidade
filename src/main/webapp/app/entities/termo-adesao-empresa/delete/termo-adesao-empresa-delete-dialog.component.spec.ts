jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TermoAdesaoEmpresaService } from '../service/termo-adesao-empresa.service';

import { TermoAdesaoEmpresaDeleteDialogComponent } from './termo-adesao-empresa-delete-dialog.component';

describe('TermoAdesaoEmpresa Management Delete Component', () => {
  let comp: TermoAdesaoEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<TermoAdesaoEmpresaDeleteDialogComponent>;
  let service: TermoAdesaoEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoAdesaoEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TermoAdesaoEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TermoAdesaoEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TermoAdesaoEmpresaService);
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
