jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AreaContabilEmpresaService } from '../service/area-contabil-empresa.service';

import { AreaContabilEmpresaDeleteDialogComponent } from './area-contabil-empresa-delete-dialog.component';

describe('AreaContabilEmpresa Management Delete Component', () => {
  let comp: AreaContabilEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<AreaContabilEmpresaDeleteDialogComponent>;
  let service: AreaContabilEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AreaContabilEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AreaContabilEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AreaContabilEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AreaContabilEmpresaService);
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
