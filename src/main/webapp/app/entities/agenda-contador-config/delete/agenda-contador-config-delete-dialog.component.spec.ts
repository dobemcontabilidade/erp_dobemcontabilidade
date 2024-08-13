jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AgendaContadorConfigService } from '../service/agenda-contador-config.service';

import { AgendaContadorConfigDeleteDialogComponent } from './agenda-contador-config-delete-dialog.component';

describe('AgendaContadorConfig Management Delete Component', () => {
  let comp: AgendaContadorConfigDeleteDialogComponent;
  let fixture: ComponentFixture<AgendaContadorConfigDeleteDialogComponent>;
  let service: AgendaContadorConfigService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaContadorConfigDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AgendaContadorConfigDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AgendaContadorConfigDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AgendaContadorConfigService);
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
