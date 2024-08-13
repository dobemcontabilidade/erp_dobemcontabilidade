import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AgendaContadorConfigDetailComponent } from './agenda-contador-config-detail.component';

describe('AgendaContadorConfig Management Detail Component', () => {
  let comp: AgendaContadorConfigDetailComponent;
  let fixture: ComponentFixture<AgendaContadorConfigDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgendaContadorConfigDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AgendaContadorConfigDetailComponent,
              resolve: { agendaContadorConfig: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AgendaContadorConfigDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgendaContadorConfigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agendaContadorConfig on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AgendaContadorConfigDetailComponent);

      // THEN
      expect(instance.agendaContadorConfig()).toEqual(expect.objectContaining({ id: 123 }));
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
