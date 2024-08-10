jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ValorBaseRamoService } from '../service/valor-base-ramo.service';

import { ValorBaseRamoDeleteDialogComponent } from './valor-base-ramo-delete-dialog.component';

describe('ValorBaseRamo Management Delete Component', () => {
  let comp: ValorBaseRamoDeleteDialogComponent;
  let fixture: ComponentFixture<ValorBaseRamoDeleteDialogComponent>;
  let service: ValorBaseRamoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ValorBaseRamoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ValorBaseRamoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ValorBaseRamoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ValorBaseRamoService);
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
