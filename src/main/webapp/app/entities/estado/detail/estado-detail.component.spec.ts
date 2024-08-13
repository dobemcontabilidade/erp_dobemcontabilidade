import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EstadoDetailComponent } from './estado-detail.component';

describe('Estado Management Detail Component', () => {
  let comp: EstadoDetailComponent;
  let fixture: ComponentFixture<EstadoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EstadoDetailComponent,
              resolve: { estado: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EstadoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstadoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load estado on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EstadoDetailComponent);

      // THEN
      expect(instance.estado()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
